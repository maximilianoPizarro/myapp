import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Prueba from './prueba';
import PruebaDetail from './prueba-detail';
import PruebaUpdate from './prueba-update';
import PruebaDeleteDialog from './prueba-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PruebaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PruebaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PruebaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Prueba} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PruebaDeleteDialog} />
  </>
);

export default Routes;
