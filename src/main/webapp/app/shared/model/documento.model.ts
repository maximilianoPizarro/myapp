export interface IDocumento {
  id?: number;
  baid?: number;
  cuil?: string;
  nombre?: string;
  apellido?: string;
  tipodocumento?: string;
}

export const defaultValue: Readonly<IDocumento> = {};
