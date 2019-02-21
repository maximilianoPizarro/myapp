import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEditorial, defaultValue } from 'app/shared/model/editorial.model';

export const ACTION_TYPES = {
  SEARCH_EDITORIALS: 'editorial/SEARCH_EDITORIALS',
  FETCH_EDITORIAL_LIST: 'editorial/FETCH_EDITORIAL_LIST',
  FETCH_EDITORIAL: 'editorial/FETCH_EDITORIAL',
  CREATE_EDITORIAL: 'editorial/CREATE_EDITORIAL',
  UPDATE_EDITORIAL: 'editorial/UPDATE_EDITORIAL',
  DELETE_EDITORIAL: 'editorial/DELETE_EDITORIAL',
  RESET: 'editorial/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEditorial>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EditorialState = Readonly<typeof initialState>;

// Reducer

export default (state: EditorialState = initialState, action): EditorialState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_EDITORIALS):
    case REQUEST(ACTION_TYPES.FETCH_EDITORIAL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EDITORIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EDITORIAL):
    case REQUEST(ACTION_TYPES.UPDATE_EDITORIAL):
    case REQUEST(ACTION_TYPES.DELETE_EDITORIAL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_EDITORIALS):
    case FAILURE(ACTION_TYPES.FETCH_EDITORIAL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EDITORIAL):
    case FAILURE(ACTION_TYPES.CREATE_EDITORIAL):
    case FAILURE(ACTION_TYPES.UPDATE_EDITORIAL):
    case FAILURE(ACTION_TYPES.DELETE_EDITORIAL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_EDITORIALS):
    case SUCCESS(ACTION_TYPES.FETCH_EDITORIAL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EDITORIAL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EDITORIAL):
    case SUCCESS(ACTION_TYPES.UPDATE_EDITORIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EDITORIAL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/editorials';
const apiSearchUrl = 'api/_search/editorials';

// Actions

export const getSearchEntities: ICrudSearchAction<IEditorial> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_EDITORIALS,
  payload: axios.get<IEditorial>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IEditorial> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EDITORIAL_LIST,
  payload: axios.get<IEditorial>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEditorial> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EDITORIAL,
    payload: axios.get<IEditorial>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEditorial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EDITORIAL,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEditorial> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EDITORIAL,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEditorial> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EDITORIAL,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
