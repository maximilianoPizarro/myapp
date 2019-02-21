import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EstablecimientoMySuffix from './establecimiento-my-suffix';
import EstablecimientoMySuffixDetail from './establecimiento-my-suffix-detail';
import EstablecimientoMySuffixUpdate from './establecimiento-my-suffix-update';
import EstablecimientoMySuffixDeleteDialog from './establecimiento-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstablecimientoMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstablecimientoMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstablecimientoMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={EstablecimientoMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EstablecimientoMySuffixDeleteDialog} />
  </>
);

export default Routes;
