import { Moment } from 'moment';
import { IAuthor } from 'app/shared/model/author.model';
import { IEditorial } from 'app/shared/model/editorial.model';

export interface IBook {
  id?: number;
  title?: string;
  description?: string;
  publicationDate?: Moment;
  price?: number;
  author?: IAuthor;
  editorials?: IEditorial[];
}

export const defaultValue: Readonly<IBook> = {};
