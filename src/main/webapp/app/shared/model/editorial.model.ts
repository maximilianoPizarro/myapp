import { Moment } from 'moment';
import { IBook } from 'app/shared/model/book.model';

export interface IEditorial {
  id?: number;
  nombre?: string;
  fecha?: Moment;
  book?: IBook;
}

export const defaultValue: Readonly<IEditorial> = {};
