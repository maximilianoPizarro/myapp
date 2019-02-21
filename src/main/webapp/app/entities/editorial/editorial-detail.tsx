import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './editorial.reducer';
import { IEditorial } from 'app/shared/model/editorial.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEditorialDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EditorialDetail extends React.Component<IEditorialDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { editorialEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="myappApp.editorial.detail.title">Editorial</Translate> [<b>{editorialEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombre">
                <Translate contentKey="myappApp.editorial.nombre">Nombre</Translate>
              </span>
            </dt>
            <dd>{editorialEntity.nombre}</dd>
            <dt>
              <span id="fecha">
                <Translate contentKey="myappApp.editorial.fecha">Fecha</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={editorialEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="myappApp.editorial.book">Book</Translate>
            </dt>
            <dd>{editorialEntity.book ? editorialEntity.book.title : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/editorial" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/editorial/${editorialEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ editorial }: IRootState) => ({
  editorialEntity: editorial.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EditorialDetail);
