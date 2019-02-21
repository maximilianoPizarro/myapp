import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './establecimiento-my-suffix.reducer';
import { IEstablecimientoMySuffix } from 'app/shared/model/establecimiento-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEstablecimientoMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEstablecimientoMySuffixUpdateState {
  isNew: boolean;
}

export class EstablecimientoMySuffixUpdate extends React.Component<
  IEstablecimientoMySuffixUpdateProps,
  IEstablecimientoMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { establecimientoEntity } = this.props;
      const entity = {
        ...establecimientoEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/establecimiento-my-suffix');
  };

  render() {
    const { establecimientoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="myappApp.establecimiento.home.createOrEditLabel">
              <Translate contentKey="myappApp.establecimiento.home.createOrEditLabel">Create or edit a Establecimiento</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : establecimientoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="establecimiento-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nroCueLabel" for="nroCue">
                    <Translate contentKey="myappApp.establecimiento.nroCue">Nro Cue</Translate>
                  </Label>
                  <AvField id="establecimiento-my-suffix-nroCue" type="string" className="form-control" name="nroCue" />
                </AvGroup>
                <AvGroup>
                  <Label id="gestionLabel" for="gestion">
                    <Translate contentKey="myappApp.establecimiento.gestion">Gestion</Translate>
                  </Label>
                  <AvField id="establecimiento-my-suffix-gestion" type="text" name="gestion" />
                </AvGroup>
                <AvGroup>
                  <Label id="modalidadLabel" for="modalidad">
                    <Translate contentKey="myappApp.establecimiento.modalidad">Modalidad</Translate>
                  </Label>
                  <AvField id="establecimiento-my-suffix-modalidad" type="text" name="modalidad" />
                </AvGroup>
                <AvGroup>
                  <Label id="nivelLabel" for="nivel">
                    <Translate contentKey="myappApp.establecimiento.nivel">Nivel</Translate>
                  </Label>
                  <AvField id="establecimiento-my-suffix-nivel" type="text" name="nivel" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/establecimiento-my-suffix" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  establecimientoEntity: storeState.establecimiento.entity,
  loading: storeState.establecimiento.loading,
  updating: storeState.establecimiento.updating,
  updateSuccess: storeState.establecimiento.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EstablecimientoMySuffixUpdate);
