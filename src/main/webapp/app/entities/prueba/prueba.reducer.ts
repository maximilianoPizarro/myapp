import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPrueba, defaultValue } from 'app/shared/model/prueba.model';

export const ACTION_TYPES = {
  SEARCH_PRUEBAS: 'prueba/SEARCH_PRUEBAS',
  FETCH_PRUEBA_LIST: 'prueba/FETCH_PRUEBA_LIST',
  FETCH_PRUEBA: 'prueba/FETCH_PRUEBA',
  CREATE_PRUEBA: 'prueba/CREATE_PRUEBA',
  UPDATE_PRUEBA: 'prueba/UPDATE_PRUEBA',
  DELETE_PRUEBA: 'prueba/DELETE_PRUEBA',
  RESET: 'prueba/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPrueba>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PruebaState = Readonly<typeof initialState>;

// Reducer

export default (state: PruebaState = initialState, action): PruebaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PRUEBAS):
    case REQUEST(ACTION_TYPES.FETCH_PRUEBA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRUEBA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRUEBA):
    case REQUEST(ACTION_TYPES.UPDATE_PRUEBA):
    case REQUEST(ACTION_TYPES.DELETE_PRUEBA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PRUEBAS):
    case FAILURE(ACTION_TYPES.FETCH_PRUEBA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRUEBA):
    case FAILURE(ACTION_TYPES.CREATE_PRUEBA):
    case FAILURE(ACTION_TYPES.UPDATE_PRUEBA):
    case FAILURE(ACTION_TYPES.DELETE_PRUEBA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PRUEBAS):
    case SUCCESS(ACTION_TYPES.FETCH_PRUEBA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRUEBA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRUEBA):
    case SUCCESS(ACTION_TYPES.UPDATE_PRUEBA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRUEBA):
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

const apiUrl = 'api/pruebas';
const apiSearchUrl = 'api/_search/pruebas';

// Actions

export const getSearchEntities: ICrudSearchAction<IPrueba> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PRUEBAS,
  payload: axios.get<IPrueba>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IPrueba> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRUEBA_LIST,
  payload: axios.get<IPrueba>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPrueba> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRUEBA,
    payload: axios.get<IPrueba>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPrueba> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRUEBA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPrueba> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRUEBA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPrueba> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRUEBA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
