import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Editorial from './editorial';
import EditorialDetail from './editorial-detail';
import EditorialUpdate from './editorial-update';
import EditorialDeleteDialog from './editorial-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EditorialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EditorialUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EditorialDetail} />
      <ErrorBoundaryRoute path={match.url} component={Editorial} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EditorialDeleteDialog} />
  </>
);

export default Routes;
