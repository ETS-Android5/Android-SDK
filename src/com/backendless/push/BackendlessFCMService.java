package com.backendless.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import com.backendless.Backendless;
import com.backendless.ContextHandler;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.messaging.AndroidPushTemplate;
import com.backendless.messaging.PublishOptions;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.concurrent.atomic.AtomicInteger;


public class BackendlessFCMService extends FirebaseMessagingService
{
  private static final String IMMEDIATE_MESSAGE = "ImmediateMessage";
  private static final String TAG = BackendlessFCMService.class.getSimpleName();
  private static AtomicInteger notificationIdGenerator;

  /**
   * <p>This method is intended for overriding.
   * <p>The notification payload can be found within intent extras: intent.getStringExtra(PublishOptions.<CONSTANT_VALUE>).
   * @param appContext Application context of current android app.
   * @param msgIntent Contains all notification data.
   * @return Return 'true' if you want backendless library to continue processing, 'false' otherwise.
   */
  public boolean onMessage( Context appContext, Intent msgIntent )
  {
    Log.i( TAG, "Notification has been received by default 'BackendlessFCMService' class. You may override this method in a custom fcm service class which extends from 'com.backendless.push.BackendlessFCMService'. The notification payload can be found within intent extras: msgIntent.getStringExtra(PublishOptions.<CONSTANT_VALUE>)." );
    return true;
  }

  public BackendlessFCMService()
  {
    if( BackendlessFCMService.notificationIdGenerator == null )
      BackendlessFCMService.notificationIdGenerator = new AtomicInteger( Backendless.getNotificationIdGeneratorInitValue() );
  }

  @Override
  public final void onNewToken( String token )
  {
    super.onNewToken( token );
    Context appContext = ContextHandler.getAppContext();
    this.refreshTokenOnBackendless( appContext, token );
  }

  @Override
  public final void onMessageReceived( RemoteMessage remoteMessage )
  {
    Intent msgIntent = remoteMessage.toIntent();
    Context appContext = ContextHandler.getAppContext();

    this.handleMessage( appContext, msgIntent );
  }

  @Override
  public void onDeletedMessages()
  {
    super.onDeletedMessages();
    Log.w( TAG, "there are too many messages (>100) pending for this app or your device hasn't connected to FCM in more than one month." );
  }

  private void handleMessage( final Context context, Intent intent )
  {
    int notificationId = BackendlessFCMService.notificationIdGenerator.getAndIncrement();
    Backendless.saveNotificationIdGeneratorState( BackendlessFCMService.notificationIdGenerator.get() );

    try
    {
      AndroidPushTemplate androidPushTemplate = null;

      String immediatePush = intent.getStringExtra( PublishOptions.ANDROID_IMMEDIATE_PUSH );
      if( immediatePush != null )
      {
        androidPushTemplate = (AndroidPushTemplate) weborb.util.io.Serializer.fromBytes( immediatePush.getBytes(), weborb.util.io.Serializer.JSON, false );
        androidPushTemplate.setName( BackendlessFCMService.IMMEDIATE_MESSAGE );
      }

      final String templateName = intent.getStringExtra( PublishOptions.TEMPLATE_NAME );
      if( immediatePush == null && templateName != null )
      {
        androidPushTemplate = PushTemplateHelper.getPushNotificationTemplates().get( templateName );
      }

      if( androidPushTemplate != null )
      {
        handleMessageWithTemplate( context, intent, androidPushTemplate, notificationId );
        return;
      }

      if( !this.onMessage( context, intent ) )
        return;

      final String message = intent.getStringExtra( PublishOptions.MESSAGE_TAG );
      final String contentTitle = intent.getStringExtra( PublishOptions.ANDROID_CONTENT_TITLE_TAG );
      final String summarySubText = intent.getStringExtra( PublishOptions.ANDROID_SUMMARY_SUBTEXT_TAG );
      String soundResource = intent.getStringExtra( PublishOptions.ANDROID_CONTENT_SOUND_TAG );
      fallBackMode( context, message, contentTitle, summarySubText, soundResource, intent.getExtras(), notificationId );
    }
    catch( Throwable throwable )
    {
      Log.e( TAG, "Error processing push notification", throwable );
    }
  }

  private void handleMessageWithTemplate( final Context context, Intent intent, AndroidPushTemplate androidPushTemplate, final int notificationId )
  {
    Bundle newBundle = PushTemplateHelper.prepareMessageBundle( intent.getExtras(), androidPushTemplate, notificationId );

    Intent newMsgIntent = new Intent();
    newMsgIntent.putExtras( newBundle );

    if( !this.onMessage( context, newMsgIntent ) )
      return;

    if( androidPushTemplate.getContentAvailable() != null && androidPushTemplate.getContentAvailable() == 1 )
      return;

    Notification notification = PushTemplateHelper.convertFromTemplate( context, androidPushTemplate, newBundle, notificationId );
    PushTemplateHelper.showNotification( context, notification, androidPushTemplate.getName(), notificationId );
  }

  private void fallBackMode( Context context, String message, String contentTitle, String summarySubText, String soundResource, Bundle bundle, final int notificationId )
  {
    final String channelName = "Fallback";
    final NotificationCompat.Builder notificationBuilder;
    Bundle newBundle = new Bundle();
    newBundle.putAll( bundle );
    newBundle.putInt( PublishOptions.NOTIFICATION_ID, notificationId );

    // android.os.Build.VERSION_CODES.O == 26
    if( android.os.Build.VERSION.SDK_INT > 25 )
    {
      final String channelId = PushTemplateHelper.getChannelId( channelName );
      NotificationManager notificationManager = (NotificationManager) context.getSystemService( Context.NOTIFICATION_SERVICE );
      NotificationChannel notificationChannel = notificationManager.getNotificationChannel( channelId );

      if( notificationChannel == null )
      {
        notificationChannel = new NotificationChannel( channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT );

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage( AudioAttributes.USAGE_NOTIFICATION_RINGTONE )
                .setContentType( AudioAttributes.CONTENT_TYPE_SONIFICATION )
                .setFlags( AudioAttributes.FLAG_AUDIBILITY_ENFORCED )
                .setLegacyStreamType( AudioManager.STREAM_NOTIFICATION )
                .build();

        notificationChannel.setSound( PushTemplateHelper.getSoundUri( context, soundResource ), audioAttributes );
        notificationManager.createNotificationChannel( notificationChannel );
      }

      notificationBuilder = new NotificationCompat.Builder( context, notificationChannel.getId() );
    }
    else
      notificationBuilder = new NotificationCompat.Builder( context );

    notificationBuilder.setSound( PushTemplateHelper.getSoundUri( context, soundResource ), AudioManager.STREAM_NOTIFICATION );

    int appIcon = context.getApplicationInfo().icon;
    if( appIcon == 0 )
      appIcon = android.R.drawable.sym_def_app_icon;

    Intent notificationIntent = context.getPackageManager().getLaunchIntentForPackage( context.getApplicationInfo().packageName );
    notificationIntent.putExtras( newBundle );
    PendingIntent contentIntent = PendingIntent.getActivity( context, notificationId * 3, notificationIntent, 0 );

    notificationBuilder.setContentIntent( contentIntent )
            .setSmallIcon( appIcon )
            .setContentTitle( contentTitle )
            .setSubText( summarySubText )
            .setContentText( message )
            .setWhen( System.currentTimeMillis() )
            .setAutoCancel( true )
            .build();

    final NotificationManagerCompat notificationManager = NotificationManagerCompat.from( context );
    Handler handler = new Handler( Looper.getMainLooper() );
    handler.post( new Runnable()
    {
      @Override
      public void run()
      {
        notificationManager.notify( channelName, notificationId, notificationBuilder.build() );
      }
    } );
  }

  private void refreshTokenOnBackendless( final Context context, String newDeviceToken )
  {
    Backendless.Messaging.refreshDeviceToken( newDeviceToken, new AsyncCallback<Boolean>()
    {
      @Override
      public void handleResponse( Boolean response )
      {
        if( response )
          Log.d( TAG, "Device token refreshed successfully." );
        else
        {
          Log.d( TAG, "Device is not registered on any channel." );
          FCMRegistration.unregisterDeviceOnFCM( context, null );
        }
      }

      @Override
      public void handleFault( BackendlessFault fault )
      {
        Log.e( TAG, "Can not refresh device token on Backendless. " + fault.getMessage() );
      }
    } );
  }
}
