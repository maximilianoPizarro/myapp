import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TituloSecundarioMySuffix from './titulo-secundario-my-suffix';
import TituloSecundarioMySuffixDetail from './titulo-secundario-my-suffix-detail';
import TituloSecundarioMySuffixUpdate from './titulo-secundario-my-suffix-update';
import TituloSecundarioMySuffixDeleteDialog from './titulo-secundario-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TituloSecundarioMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TituloSecundarioMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TituloSecundarioMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={TituloSecundarioMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TituloSecundarioMySuffixDeleteDialog} />
  </>
);

export default Routes;
