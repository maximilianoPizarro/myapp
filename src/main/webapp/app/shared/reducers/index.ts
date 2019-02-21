import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import author, {
  AuthorState
} from 'app/entities/author/author.reducer';
// prettier-ignore
import book, {
  BookState
} from 'app/entities/book/book.reducer';
// prettier-ignore
import editorial, {
  EditorialState
} from 'app/entities/editorial/editorial.reducer';
// prettier-ignore
import tituloSecundario, {
  TituloSecundarioMySuffixState
} from 'app/entities/titulo-secundario-my-suffix/titulo-secundario-my-suffix.reducer';
// prettier-ignore
import establecimiento, {
  EstablecimientoMySuffixState
} from 'app/entities/establecimiento-my-suffix/establecimiento-my-suffix.reducer';
// prettier-ignore
import documento, {
  DocumentoState
} from 'app/entities/documento/documento.reducer';
// prettier-ignore
import prueba, {
  PruebaState
} from 'app/entities/prueba/prueba.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly author: AuthorState;
  readonly book: BookState;
  readonly editorial: EditorialState;
  readonly tituloSecundario: TituloSecundarioMySuffixState;
  readonly establecimiento: EstablecimientoMySuffixState;
  readonly documento: DocumentoState;
  readonly prueba: PruebaState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  author,
  book,
  editorial,
  tituloSecundario,
  establecimiento,
  documento,
  prueba,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
