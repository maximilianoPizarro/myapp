import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEstablecimientoMySuffix, defaultValue } from 'app/shared/model/establecimiento-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_ESTABLECIMIENTOS: 'establecimiento/SEARCH_ESTABLECIMIENTOS',
  FETCH_ESTABLECIMIENTO_LIST: 'establecimiento/FETCH_ESTABLECIMIENTO_LIST',
  FETCH_ESTABLECIMIENTO: 'establecimiento/FETCH_ESTABLECIMIENTO',
  CREATE_ESTABLECIMIENTO: 'establecimiento/CREATE_ESTABLECIMIENTO',
  UPDATE_ESTABLECIMIENTO: 'establecimiento/UPDATE_ESTABLECIMIENTO',
  DELETE_ESTABLECIMIENTO: 'establecimiento/DELETE_ESTABLECIMIENTO',
  RESET: 'establecimiento/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEstablecimientoMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EstablecimientoMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: EstablecimientoMySuffixState = initialState, action): EstablecimientoMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ESTABLECIMIENTOS):
    case REQUEST(ACTION_TYPES.FETCH_ESTABLECIMIENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ESTABLECIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ESTABLECIMIENTO):
    case REQUEST(ACTION_TYPES.UPDATE_ESTABLECIMIENTO):
    case REQUEST(ACTION_TYPES.DELETE_ESTABLECIMIENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ESTABLECIMIENTOS):
    case FAILURE(ACTION_TYPES.FETCH_ESTABLECIMIENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ESTABLECIMIENTO):
    case FAILURE(ACTION_TYPES.CREATE_ESTABLECIMIENTO):
    case FAILURE(ACTION_TYPES.UPDATE_ESTABLECIMIENTO):
    case FAILURE(ACTION_TYPES.DELETE_ESTABLECIMIENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ESTABLECIMIENTOS):
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLECIMIENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ESTABLECIMIENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ESTABLECIMIENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_ESTABLECIMIENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ESTABLECIMIENTO):
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

const apiUrl = 'api/establecimientos';
const apiSearchUrl = 'api/_search/establecimientos';

// Actions

export const getSearchEntities: ICrudSearchAction<IEstablecimientoMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ESTABLECIMIENTOS,
  payload: axios.get<IEstablecimientoMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IEstablecimientoMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ESTABLECIMIENTO_LIST,
  payload: axios.get<IEstablecimientoMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEstablecimientoMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ESTABLECIMIENTO,
    payload: axios.get<IEstablecimientoMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEstablecimientoMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ESTABLECIMIENTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEstablecimientoMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ESTABLECIMIENTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEstablecimientoMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ESTABLECIMIENTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
