import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDocumento, defaultValue } from 'app/shared/model/documento.model';

export const ACTION_TYPES = {
  SEARCH_DOCUMENTOS: 'documento/SEARCH_DOCUMENTOS',
  FETCH_DOCUMENTO_LIST: 'documento/FETCH_DOCUMENTO_LIST',
  FETCH_DOCUMENTO: 'documento/FETCH_DOCUMENTO',
  CREATE_DOCUMENTO: 'documento/CREATE_DOCUMENTO',
  UPDATE_DOCUMENTO: 'documento/UPDATE_DOCUMENTO',
  DELETE_DOCUMENTO: 'documento/DELETE_DOCUMENTO',
  RESET: 'documento/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDocumento>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DocumentoState = Readonly<typeof initialState>;

// Reducer

export default (state: DocumentoState = initialState, action): DocumentoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_DOCUMENTOS):
    case REQUEST(ACTION_TYPES.FETCH_DOCUMENTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DOCUMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DOCUMENTO):
    case REQUEST(ACTION_TYPES.UPDATE_DOCUMENTO):
    case REQUEST(ACTION_TYPES.DELETE_DOCUMENTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_DOCUMENTOS):
    case FAILURE(ACTION_TYPES.FETCH_DOCUMENTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DOCUMENTO):
    case FAILURE(ACTION_TYPES.CREATE_DOCUMENTO):
    case FAILURE(ACTION_TYPES.UPDATE_DOCUMENTO):
    case FAILURE(ACTION_TYPES.DELETE_DOCUMENTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_DOCUMENTOS):
    case SUCCESS(ACTION_TYPES.FETCH_DOCUMENTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DOCUMENTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DOCUMENTO):
    case SUCCESS(ACTION_TYPES.UPDATE_DOCUMENTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DOCUMENTO):
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

const apiUrl = 'api/documentos';
const apiSearchUrl = 'api/_search/documentos';

// Actions

export const getSearchEntities: ICrudSearchAction<IDocumento> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_DOCUMENTOS,
  payload: axios.get<IDocumento>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IDocumento> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DOCUMENTO_LIST,
  payload: axios.get<IDocumento>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDocumento> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DOCUMENTO,
    payload: axios.get<IDocumento>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDocumento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DOCUMENTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDocumento> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DOCUMENTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDocumento> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DOCUMENTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
