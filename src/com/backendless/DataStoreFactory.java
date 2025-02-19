/*
 * ********************************************************************************************************************
 *  <p/>
 *  BACKENDLESS.COM CONFIDENTIAL
 *  <p/>
 *  ********************************************************************************************************************
 *  <p/>
 *  Copyright 2(Integer)null12 BACKENDLESS.COM. All Rights Reserved.
 *  <p/>
 *  NOTICE: All information contained herein is, and remains the property of Backendless.com and its suppliers,
 *  if any. The intellectual and technical concepts contained herein are proprietary to Backendless.com and its
 *  suppliers and may be covered by U.S. and Foreign Patents, patents in process, and are protected by trade secret
 *  or copyright law. Dissemination of this information or reproduction of this material is strictly forbidden
 *  unless prior written permission is obtained from Backendless.com.
 *  <p/>
 *  ********************************************************************************************************************
 */

package com.backendless;

import android.support.annotation.NonNull;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.commons.persistence.GroupResult;
import com.backendless.exceptions.BackendlessException;
import com.backendless.persistence.BackendlessSerializer;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.GroupDataQueryBuilder;
import com.backendless.persistence.LoadRelationsQueryBuilder;
import com.backendless.rt.data.EventHandler;
import com.backendless.rt.data.EventHandlerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class DataStoreFactory
{
  private static final List<String> emptyRelations = new ArrayList<String>();
  private final static EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();

  protected static <E> IDataStore<E> createDataStore( final Class<E> entityClass )
  {

    return new IDataStore<E>()
    {
      private EventHandler<E> eventHandler = eventHandlerFactory.of( entityClass );

      @Override
      public List<String> create( List<E> objects ) throws BackendlessException
      {
        return Backendless.Persistence.create( objects );
      }

      @Override
      public void create( List<E> objects, AsyncCallback<List<String>> responder ) throws BackendlessException
      {
        Backendless.Persistence.create( objects, responder );
      }

      @Override
      public E save( final E entity ) throws BackendlessException
      {
        return Backendless.Persistence.save( entity );
      }

      @Override
      public E save( final E entity, boolean isUpsert ) throws BackendlessException
      {
        return Backendless.Persistence.save( entity );
      }

      @Override
      public void save( final E entity, final AsyncCallback<E> responder )
      {
        Backendless.Persistence.save( entity, responder );
      }

      @Override
      public void save( final E entity, boolean isUpsert, final AsyncCallback<E> responder )
      {
        Backendless.Persistence.save( entity, responder );
      }

      @Override
      public E deepSave( final E entity ) throws BackendlessException
      {
        return Backendless.Persistence.deepSave( entity );
      }

      @Override
      public void deepSave( final E entity, final AsyncCallback<E> responder )
      {
        Backendless.Persistence.deepSave( entity, responder );
      }

      @Override
      public Long remove( final E entity ) throws BackendlessException
      {
        return Backendless.Persistence.remove( entity );
      }

      @Override
      public void remove( final E entity, final AsyncCallback<Long> responder )
      {
        Backendless.Persistence.remove( entity, responder );
      }

      @Override
      public int remove( final String whereClause ) throws BackendlessException
      {
        return Backendless.Persistence.remove( BackendlessSerializer.getSimpleName( entityClass ), whereClause );
      }

      @Override
      public void remove( final String whereClause, AsyncCallback<Integer> responder ) throws BackendlessException
      {
        Backendless.Persistence.remove( BackendlessSerializer.getSimpleName( entityClass ), whereClause, responder );
      }

      @Override
      public int update( final String whereClause, Map<String, Object> changes ) throws BackendlessException
      {
        return Backendless.Persistence.update( BackendlessSerializer.getSimpleName( entityClass ), whereClause, changes );
      }

      @Override
      public void update( final String whereClause, Map<String, Object> changes, AsyncCallback<Integer> responder ) throws BackendlessException
      {
        Backendless.Persistence.update( BackendlessSerializer.getSimpleName( entityClass ), whereClause, changes, responder );
      }

      @Override
      public E findFirst() throws BackendlessException
      {
        return Backendless.Persistence.first( entityClass );
      }

      @Override
      public E findFirst( Integer relationsDepth ) throws BackendlessException
      {
        return findFirst( emptyRelations, relationsDepth, null );
      }

      @Override
      public E findFirst( List<String> relations ) throws BackendlessException
      {
        return findFirst( relations, null, null );
      }

      @Override
      public E findFirst( List<String> relations, Integer relationsDepth, Integer relationsPageSize ) throws BackendlessException
      {
        return Backendless.Persistence.first( entityClass, relations, relationsDepth, relationsPageSize );
      }

      @Override
      public int getObjectCount()
      {
        return Backendless.Persistence.getObjectCount( entityClass );
      }

      @Override
      public int getObjectCount( DataQueryBuilder dataQueryBuilder )
      {
        return Backendless.Persistence.getObjectCount( entityClass, dataQueryBuilder );
      }

      @Override
      public int getObjectCountInGroup( GroupDataQueryBuilder dataQueryBuilder )
      {
        return Backendless.Persistence.getObjectCountInGroup( entityClass, dataQueryBuilder );
      }

      public void findFirst( final AsyncCallback<E> responder )
      {
        Backendless.Persistence.first( entityClass, responder );
      }

      @Override
      public void findFirst( Integer relationsDepth, final AsyncCallback<E> responder )
      {
        findFirst( emptyRelations, relationsDepth, null, responder );
      }

      @Override
      public void findFirst( List<String> relations, AsyncCallback<E> responder )
      {
        findFirst( relations, null, null, responder );
      }

      @Override
      public void findFirst( List<String> relations, Integer relationsDepth, Integer relationsPageSize, final AsyncCallback<E> responder )
      {
        Backendless.Persistence.first( entityClass, relations, relationsDepth, relationsPageSize, responder );
      }

      @Override
      public E findLast() throws BackendlessException
      {
        return Backendless.Persistence.last( entityClass );
      }

      @Override
      public E findLast( Integer relationsDepth ) throws BackendlessException
      {
        return findLast( emptyRelations, relationsDepth, null );
      }

      @Override
      public E findLast( List<String> relations ) throws BackendlessException
      {
        return findLast( relations, (Integer)null, null );
      }

      @Override
      public E findLast( List<String> relations, Integer relationsDepth, Integer relationsPageSize ) throws BackendlessException
      {
        return Backendless.Persistence.last( entityClass, relations, relationsDepth, relationsPageSize );
      }

      @Override
      public void findLast( final AsyncCallback<E> responder )
      {
        Backendless.Persistence.last( entityClass, responder );
      }

      @Override
      public void findLast( Integer relationsDepth, final AsyncCallback<E> responder )
      {
        findLast( emptyRelations, relationsDepth, null, responder );
      }

      @Override
      public void findLast( List<String> relations, AsyncCallback<E> responder )
      {
        findLast( relations, null, null, responder );
      }

      @Override
      public void findLast( List<String> relations, Integer relationsDepth, Integer relationsPageSize, final AsyncCallback<E> responder )
      {
        Backendless.Persistence.last( entityClass, relations, relationsDepth, relationsPageSize, responder );
      }

      @Override
      public List<E> find() throws BackendlessException
      {
        return Backendless.Persistence.find( entityClass, DataQueryBuilder.create() );
      }

      @Override
      public List<E> find( DataQueryBuilder dataQueryBuilder ) throws BackendlessException
      {
        return Backendless.Persistence.find( entityClass, dataQueryBuilder );
      }

      @Override
      public void find( AsyncCallback<List<E>> responder )
      {
        Backendless.Persistence.find( entityClass, DataQueryBuilder.create(), responder );
      }

      @Override
      public void find( DataQueryBuilder dataQueryBuilder, AsyncCallback<List<E>> responder )
      {
        Backendless.Persistence.find( entityClass, dataQueryBuilder, responder );
      }

      @Override
      public GroupResult<?,E> group( GroupDataQueryBuilder dataQueryBuilder ) throws BackendlessException
      {
        return Backendless.Persistence.group( entityClass, dataQueryBuilder );
      }

      @Override
      public void group( GroupDataQueryBuilder dataQueryBuilder, AsyncCallback<GroupResult<?,E>> responder )
      {
        Backendless.Persistence.group( entityClass, dataQueryBuilder, responder );
      }

      @Override
      public E findById( String objectId ) throws BackendlessException
      {
        return findById( objectId, emptyRelations );
      }

      @Override
      public E findById( String objectId, List<String> relations ) throws BackendlessException
      {
        return Backendless.Persistence.findById( entityClass, objectId, relations );
      }

      @Override
      public E findById( String objectId, Integer relationsDepth ) throws BackendlessException
      {
        return Backendless.Persistence.findById( entityClass, objectId, emptyRelations, relationsDepth );
      }

      @Override
      public E findById( String objectId, List<String> relations, Integer relationsDepth ) throws BackendlessException
      {
        return Backendless.Persistence.findById( entityClass, objectId, relations, relationsDepth );
      }

      @Override
      public E findById( E entity )
      {
        return findById( entity, emptyRelations );
      }

      @Override
      public E findById( E entity, List<String> relations )
      {
        return findById( entity, relations, (Integer)null );
      }

      @Override
      public E findById( E entity, Integer relationsDepth )
      {
        return findById( entity, emptyRelations, relationsDepth );
      }

      @Override
      public E findById( E entity, List<String> relations, Integer relationsDepth )
      {
        return Backendless.Data.findById( entity, relations, relationsDepth );
      }

      @Override
      public void findById( String objectId, AsyncCallback<E> responder )
      {
        findById( objectId, emptyRelations, responder );
      }

      @Override
      public void findById( String objectId, List<String> relations, AsyncCallback<E> responder )
      {
        Backendless.Persistence.findById( entityClass, objectId, relations, responder );
      }

      @Override
      public void findById( String objectId, Integer relationsDepth, AsyncCallback<E> responder )
      {
        findById( objectId, emptyRelations, relationsDepth, responder );
      }

      @Override
      public void findById( String objectId, List<String> relations, Integer relationsDepth, AsyncCallback<E> responder )
      {
        Backendless.Persistence.findById( entityClass, objectId, relations, relationsDepth, responder );
      }

      @Override
      public void findById( E entity, AsyncCallback<E> responder )
      {
        findById( entity, emptyRelations, responder );
      }

      @Override
      public void findById( E entity, List<String> relations, AsyncCallback<E> responder )
      {
        findById( entity, relations, (Integer)null, responder );
      }

      @Override
      public void findById( E entity, Integer relationsDepth, AsyncCallback<E> responder )
      {
        findById( entity, emptyRelations, relationsDepth, responder );
      }

      @Override
      public void findById( E entity, List<String> relations, Integer relationsDepth, AsyncCallback<E> responder )
      {
        Backendless.Data.findById( entity, relations, relationsDepth, responder );
      }

      @Override
      public E findById( String id, DataQueryBuilder queryBuilder ) throws BackendlessException
      {
        return Backendless.Persistence.findById( entityClass, id, queryBuilder );
      }

      @Override
      public E findById( E entity, DataQueryBuilder queryBuilder ) throws BackendlessException
      {
        return Backendless.Persistence.findById( entity, queryBuilder );
      }

      @Override
      public void findById( String id, DataQueryBuilder queryBuilder, AsyncCallback<E> responder )
      {
        Backendless.Persistence.findById( entityClass, id, queryBuilder, responder );
      }

      @Override
      public void findById( E entity, DataQueryBuilder queryBuilder, AsyncCallback<E> responder )
      {
        Backendless.Persistence.findById( entity, queryBuilder, responder );
      }

      @Override
      public <R> List<R> loadRelations( String objectId, LoadRelationsQueryBuilder<R> queryBuilder )
      {
        String typeName = BackendlessSerializer.getSimpleName( entityClass );
        return Backendless.Persistence.loadRelations( typeName, objectId, queryBuilder, queryBuilder.getRelationType() );
      }

      @Override
      public <R> void loadRelations( String objectId, LoadRelationsQueryBuilder<R> queryBuilder, AsyncCallback<List<R>> responder )
      {
        String typeName = BackendlessSerializer.getSimpleName( entityClass );
        Backendless.Persistence.loadRelations( typeName, objectId, queryBuilder, queryBuilder.getRelationType(), responder );
      }

      @Override
      public void getObjectCount( AsyncCallback<Integer> responder )
      {
        Backendless.Persistence.getObjectCount( entityClass, responder );
      }

      @Override
      public void getObjectCount( DataQueryBuilder dataQueryBuilder, AsyncCallback<Integer> responder )
      {
        Backendless.Persistence.getObjectCount( entityClass, dataQueryBuilder, responder );
      }

      @Override
      public void getObjectCountInGroup( GroupDataQueryBuilder dataQueryBuilder, AsyncCallback<Integer> responder )
      {
        Backendless.Persistence.getObjectCountInGroup( entityClass, dataQueryBuilder, responder );
      }

      @Override
      public <R> int addRelation( E parent, String relationColumnName, Collection<R> children )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        return addRelation( parentObjectId, relationColumnName, childrenObjectIds );
      }

      @Override
      public <R> void addRelation( E parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        addRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
      }

      @Override
      public int addRelation( E parent, String relationColumnName, String whereClause )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        return addRelation( parentObjectId, relationColumnName, whereClause );
      }

      @Override
      public void addRelation( E parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        addRelation( parentObjectId, relationColumnName, whereClause, callback );
      }

      @Override
      public <R> int setRelation( E parent, String relationColumnName, Collection<R> children )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        return setRelation( parentObjectId, relationColumnName, childrenObjectIds );
      }

      @Override
      public <R> void setRelation( E parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        setRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
      }

      @Override
      public int setRelation( E parent, String relationColumnName, String whereClause )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        return setRelation( parentObjectId, relationColumnName, whereClause );
      }

      @Override
      public void setRelation( E parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        setRelation( parentObjectId, relationColumnName, whereClause, callback );
      }

      @Override
      public <R> int deleteRelation( E parent, String relationColumnName, Collection<R> children )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        return deleteRelation( parentObjectId, relationColumnName, childrenObjectIds );
      }

      @Override
      public <R> void deleteRelation( E parent, String relationColumnName, Collection<R> children, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        Collection<String> childrenObjectIds = new ArrayList<>();
        for( R child : children )
        {
          String childObjectId = Persistence.getEntityId( child );
          childrenObjectIds.add( childObjectId );
        }

        deleteRelation( parentObjectId, relationColumnName, childrenObjectIds, callback );
      }

      @Override
      public int deleteRelation( E parent, String relationColumnName, String whereClause )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        return deleteRelation( parentObjectId, relationColumnName, whereClause );
      }

      @Override
      public void deleteRelation( E parent, String relationColumnName, String whereClause, AsyncCallback<Integer> callback )
      {
        String parentObjectId = Persistence.getEntityId( parent );

        deleteRelation( parentObjectId, relationColumnName, whereClause, callback );
      }

      @Override
      public <R> int addRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args );
      }

      @Override
      public <R> void addRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args, callback );
      }

      @Override
      public int addRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args );
      }

      @Override
      public void addRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "addRelation", args, callback );
      }

      @Override
      public <R> int setRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args );
      }

      @Override
      public <R> void setRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args, callback );
      }

      @Override
      public int setRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args );
      }

      @Override
      public void setRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "setRelation", args, callback );
      }

      @Override
      public <R> int deleteRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args );
      }

      @Override
      public <R> void deleteRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull Collection<String> childrenObjectIds, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );

        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, childrenObjectIds };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args, callback );
      }

      @Override
      public int deleteRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        return Invoker.invokeSync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args );
      }

      @Override
      public void deleteRelation(@NonNull String parentObjectId, @NonNull String relationColumnName, @NonNull String whereClause, AsyncCallback<Integer> callback )
      {
        String parentTableName = BackendlessSerializer.getSimpleName( entityClass );


        Object[] args = new Object[] { parentTableName, relationColumnName, parentObjectId, whereClause };
        Invoker.invokeAsync( Persistence.PERSISTENCE_MANAGER_SERVER_ALIAS, "deleteRelation", args, callback );
      }

      @Override
      public EventHandler<E> rt()
      {
        return eventHandler;
      }
    };
  }
}
