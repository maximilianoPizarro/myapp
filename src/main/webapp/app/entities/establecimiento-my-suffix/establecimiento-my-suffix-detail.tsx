import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './establecimiento-my-suffix.reducer';
import { IEstablecimientoMySuffix } from 'app/shared/model/establecimiento-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEstablecimientoMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EstablecimientoMySuffixDetail extends React.Component<IEstablecimientoMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { establecimientoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="myappApp.establecimiento.detail.title">Establecimiento</Translate> [<b>{establecimientoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nroCue">
                <Translate contentKey="myappApp.establecimiento.nroCue">Nro Cue</Translate>
              </span>
            </dt>
            <dd>{establecimientoEntity.nroCue}</dd>
            <dt>
              <span id="gestion">
                <Translate contentKey="myappApp.establecimiento.gestion">Gestion</Translate>
              </span>
            </dt>
            <dd>{establecimientoEntity.gestion}</dd>
            <dt>
              <span id="modalidad">
                <Translate contentKey="myappApp.establecimiento.modalidad">Modalidad</Translate>
              </span>
            </dt>
            <dd>{establecimientoEntity.modalidad}</dd>
            <dt>
              <span id="nivel">
                <Translate contentKey="myappApp.establecimiento.nivel">Nivel</Translate>
              </span>
            </dt>
            <dd>{establecimientoEntity.nivel}</dd>
          </dl>
          <Button tag={Link} to="/entity/establecimiento-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/establecimiento-my-suffix/${establecimientoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ establecimiento }: IRootState) => ({
  establecimientoEntity: establecimiento.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EstablecimientoMySuffixDetail);
