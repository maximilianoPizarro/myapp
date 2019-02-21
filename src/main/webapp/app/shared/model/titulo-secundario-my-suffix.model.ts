import { Moment } from 'moment';
import { IEstablecimientoMySuffix } from 'app/shared/model/establecimiento-my-suffix.model';

export const enum TipoEjemplar {
  ORIGINAL = 'ORIGINAL',
  DUPLICADO = 'DUPLICADO',
  TRIPLICADO = 'TRIPLICADO',
  CUADRIPLICADO = 'CUADRIPLICADO',
  QUINTUPLICADO = 'QUINTUPLICADO',
  SEXTUPLICADO = 'SEXTUPLICADO'
}

export const enum TipoDni {
  DNI = 'DNI',
  PAS = 'PAS',
  DE = 'DE',
  CRP = 'CRP'
}

export interface ITituloSecundarioMySuffix {
  id?: number;
  nroTitulo?: number;
  tipoEjemplar?: TipoEjemplar;
  nombre?: string;
  apellido?: string;
  dni?: TipoDni;
  fechaNacimiento?: Moment;
  tituloOtorgado?: string;
  promedio?: number;
  mesAnnioEgreso?: string;
  validezNacional?: number;
  dictamen?: string;
  revisado?: string;
  ingreso?: Moment;
  egreso?: Moment;
  nroCue?: IEstablecimientoMySuffix;
}

export const defaultValue: Readonly<ITituloSecundarioMySuffix> = {};
