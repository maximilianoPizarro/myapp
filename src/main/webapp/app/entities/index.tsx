import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Author from './author';
import Book from './book';
import Editorial from './editorial';
import TituloSecundarioMySuffix from './titulo-secundario-my-suffix';
import EstablecimientoMySuffix from './establecimiento-my-suffix';
import Documento from './documento';
import Prueba from './prueba';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/author`} component={Author} />
      <ErrorBoundaryRoute path={`${match.url}/book`} component={Book} />
      <ErrorBoundaryRoute path={`${match.url}/editorial`} component={Editorial} />
      <ErrorBoundaryRoute path={`${match.url}/titulo-secundario-my-suffix`} component={TituloSecundarioMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/establecimiento-my-suffix`} component={EstablecimientoMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/documento`} component={Documento} />
      <ErrorBoundaryRoute path={`${match.url}/prueba`} component={Prueba} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
