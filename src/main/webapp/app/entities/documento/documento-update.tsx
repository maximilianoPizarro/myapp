import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './documento.reducer';
import { IDocumento } from 'app/shared/model/documento.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDocumentoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDocumentoUpdateState {
  isNew: boolean;
}

export class DocumentoUpdate extends React.Component<IDocumentoUpdateProps, IDocumentoUpdateState> {
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
      const { documentoEntity } = this.props;
      const entity = {
        ...documentoEntity,
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
    this.props.history.push('/entity/documento');
  };

  render() {
    const { documentoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="myappApp.documento.home.createOrEditLabel">
              <Translate contentKey="myappApp.documento.home.createOrEditLabel">Create or edit a Documento</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : documentoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="documento-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="baidLabel" for="baid">
                    <Translate contentKey="myappApp.documento.baid">Baid</Translate>
                  </Label>
                  <AvField id="documento-baid" type="string" className="form-control" name="baid" />
                </AvGroup>
                <AvGroup>
                  <Label id="cuilLabel" for="cuil">
                    <Translate contentKey="myappApp.documento.cuil">Cuil</Translate>
                  </Label>
                  <AvField id="documento-cuil" type="text" name="cuil" />
                </AvGroup>
                <AvGroup>
                  <Label id="nombreLabel" for="nombre">
                    <Translate contentKey="myappApp.documento.nombre">Nombre</Translate>
                  </Label>
                  <AvField id="documento-nombre" type="text" name="nombre" />
                </AvGroup>
                <AvGroup>
                  <Label id="apellidoLabel" for="apellido">
                    <Translate contentKey="myappApp.documento.apellido">Apellido</Translate>
                  </Label>
                  <AvField id="documento-apellido" type="text" name="apellido" />
                </AvGroup>
                <AvGroup>
                  <Label id="tipodocumentoLabel" for="tipodocumento">
                    <Translate contentKey="myappApp.documento.tipodocumento">Tipodocumento</Translate>
                  </Label>
                  <AvField id="documento-tipodocumento" type="text" name="tipodocumento" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/documento" replace color="info">
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
  documentoEntity: storeState.documento.entity,
  loading: storeState.documento.loading,
  updating: storeState.documento.updating,
  updateSuccess: storeState.documento.updateSuccess
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
)(DocumentoUpdate);
