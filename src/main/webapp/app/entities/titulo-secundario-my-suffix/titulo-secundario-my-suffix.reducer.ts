import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITituloSecundarioMySuffix, defaultValue } from 'app/shared/model/titulo-secundario-my-suffix.model';

export const ACTION_TYPES = {
  SEARCH_TITULOSECUNDARIOS: 'tituloSecundario/SEARCH_TITULOSECUNDARIOS',
  FETCH_TITULOSECUNDARIO_LIST: 'tituloSecundario/FETCH_TITULOSECUNDARIO_LIST',
  FETCH_TITULOSECUNDARIO: 'tituloSecundario/FETCH_TITULOSECUNDARIO',
  CREATE_TITULOSECUNDARIO: 'tituloSecundario/CREATE_TITULOSECUNDARIO',
  UPDATE_TITULOSECUNDARIO: 'tituloSecundario/UPDATE_TITULOSECUNDARIO',
  DELETE_TITULOSECUNDARIO: 'tituloSecundario/DELETE_TITULOSECUNDARIO',
  RESET: 'tituloSecundario/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITituloSecundarioMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TituloSecundarioMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: TituloSecundarioMySuffixState = initialState, action): TituloSecundarioMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TITULOSECUNDARIOS):
    case REQUEST(ACTION_TYPES.FETCH_TITULOSECUNDARIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TITULOSECUNDARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TITULOSECUNDARIO):
    case REQUEST(ACTION_TYPES.UPDATE_TITULOSECUNDARIO):
    case REQUEST(ACTION_TYPES.DELETE_TITULOSECUNDARIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TITULOSECUNDARIOS):
    case FAILURE(ACTION_TYPES.FETCH_TITULOSECUNDARIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TITULOSECUNDARIO):
    case FAILURE(ACTION_TYPES.CREATE_TITULOSECUNDARIO):
    case FAILURE(ACTION_TYPES.UPDATE_TITULOSECUNDARIO):
    case FAILURE(ACTION_TYPES.DELETE_TITULOSECUNDARIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TITULOSECUNDARIOS):
    case SUCCESS(ACTION_TYPES.FETCH_TITULOSECUNDARIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TITULOSECUNDARIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TITULOSECUNDARIO):
    case SUCCESS(ACTION_TYPES.UPDATE_TITULOSECUNDARIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TITULOSECUNDARIO):
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

const apiUrl = 'api/titulo-secundarios';
const apiSearchUrl = 'api/_search/titulo-secundarios';

// Actions

export const getSearchEntities: ICrudSearchAction<ITituloSecundarioMySuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TITULOSECUNDARIOS,
  payload: axios.get<ITituloSecundarioMySuffix>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITituloSecundarioMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TITULOSECUNDARIO_LIST,
  payload: axios.get<ITituloSecundarioMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITituloSecundarioMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TITULOSECUNDARIO,
    payload: axios.get<ITituloSecundarioMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITituloSecundarioMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TITULOSECUNDARIO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITituloSecundarioMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TITULOSECUNDARIO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITituloSecundarioMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TITULOSECUNDARIO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
