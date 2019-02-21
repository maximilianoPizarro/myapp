import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IEstablecimientoMySuffix } from 'app/shared/model/establecimiento-my-suffix.model';
import { getEntities as getEstablecimientos } from 'app/entities/establecimiento-my-suffix/establecimiento-my-suffix.reducer';
import { getEntity, updateEntity, createEntity, reset } from './titulo-secundario-my-suffix.reducer';
import { ITituloSecundarioMySuffix } from 'app/shared/model/titulo-secundario-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITituloSecundarioMySuffixUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ITituloSecundarioMySuffixUpdateState {
  isNew: boolean;
  nroCueId: string;
}

export class TituloSecundarioMySuffixUpdate extends React.Component<
  ITituloSecundarioMySuffixUpdateProps,
  ITituloSecundarioMySuffixUpdateState
> {
  constructor(props) {
    super(props);
    this.state = {
      nroCueId: '0',
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

    this.props.getEstablecimientos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { tituloSecundarioEntity } = this.props;
      const entity = {
        ...tituloSecundarioEntity,
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
    this.props.history.push('/entity/titulo-secundario-my-suffix');
  };

  render() {
    const { tituloSecundarioEntity, establecimientos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="myappApp.tituloSecundario.home.createOrEditLabel">
              <Translate contentKey="myappApp.tituloSecundario.home.createOrEditLabel">Create or edit a TituloSecundario</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : tituloSecundarioEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="titulo-secundario-my-suffix-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nroTituloLabel" for="nroTitulo">
                    <Translate contentKey="myappApp.tituloSecundario.nroTitulo">Nro Titulo</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-nroTitulo"
                    type="string"
                    className="form-control"
                    name="nroTitulo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tipoEjemplarLabel">
                    <Translate contentKey="myappApp.tituloSecundario.tipoEjemplar">Tipo Ejemplar</Translate>
                  </Label>
                  <AvInput
                    id="titulo-secundario-my-suffix-tipoEjemplar"
                    type="select"
                    className="form-control"
                    name="tipoEjemplar"
                    value={(!isNew && tituloSecundarioEntity.tipoEjemplar) || 'ORIGINAL'}
                  >
                    <option value="ORIGINAL">
                      <Translate contentKey="myappApp.TipoEjemplar.ORIGINAL" />
                    </option>
                    <option value="DUPLICADO">
                      <Translate contentKey="myappApp.TipoEjemplar.DUPLICADO" />
                    </option>
                    <option value="TRIPLICADO">
                      <Translate contentKey="myappApp.TipoEjemplar.TRIPLICADO" />
                    </option>
                    <option value="CUADRIPLICADO">
                      <Translate contentKey="myappApp.TipoEjemplar.CUADRIPLICADO" />
                    </option>
                    <option value="QUINTUPLICADO">
                      <Translate contentKey="myappApp.TipoEjemplar.QUINTUPLICADO" />
                    </option>
                    <option value="SEXTUPLICADO">
                      <Translate contentKey="myappApp.TipoEjemplar.SEXTUPLICADO" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="nombreLabel" for="nombre">
                    <Translate contentKey="myappApp.tituloSecundario.nombre">Nombre</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-nombre"
                    type="text"
                    name="nombre"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="apellidoLabel" for="apellido">
                    <Translate contentKey="myappApp.tituloSecundario.apellido">Apellido</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-apellido"
                    type="text"
                    name="apellido"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dniLabel">
                    <Translate contentKey="myappApp.tituloSecundario.dni">Dni</Translate>
                  </Label>
                  <AvInput
                    id="titulo-secundario-my-suffix-dni"
                    type="select"
                    className="form-control"
                    name="dni"
                    value={(!isNew && tituloSecundarioEntity.dni) || 'DNI'}
                  >
                    <option value="DNI">
                      <Translate contentKey="myappApp.TipoDni.DNI" />
                    </option>
                    <option value="PAS">
                      <Translate contentKey="myappApp.TipoDni.PAS" />
                    </option>
                    <option value="DE">
                      <Translate contentKey="myappApp.TipoDni.DE" />
                    </option>
                    <option value="CRP">
                      <Translate contentKey="myappApp.TipoDni.CRP" />
                    </option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="fechaNacimientoLabel" for="fechaNacimiento">
                    <Translate contentKey="myappApp.tituloSecundario.fechaNacimiento">Fecha Nacimiento</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-fechaNacimiento"
                    type="date"
                    className="form-control"
                    name="fechaNacimiento"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="tituloOtorgadoLabel" for="tituloOtorgado">
                    <Translate contentKey="myappApp.tituloSecundario.tituloOtorgado">Titulo Otorgado</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-tituloOtorgado"
                    type="text"
                    name="tituloOtorgado"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="promedioLabel" for="promedio">
                    <Translate contentKey="myappApp.tituloSecundario.promedio">Promedio</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-promedio"
                    type="string"
                    className="form-control"
                    name="promedio"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="mesAnnioEgresoLabel" for="mesAnnioEgreso">
                    <Translate contentKey="myappApp.tituloSecundario.mesAnnioEgreso">Mes Annio Egreso</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-mesAnnioEgreso"
                    type="text"
                    name="mesAnnioEgreso"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="validezNacionalLabel" for="validezNacional">
                    <Translate contentKey="myappApp.tituloSecundario.validezNacional">Validez Nacional</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-validezNacional"
                    type="string"
                    className="form-control"
                    name="validezNacional"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="dictamenLabel" for="dictamen">
                    <Translate contentKey="myappApp.tituloSecundario.dictamen">Dictamen</Translate>
                  </Label>
                  <AvField id="titulo-secundario-my-suffix-dictamen" type="text" name="dictamen" />
                </AvGroup>
                <AvGroup>
                  <Label id="revisadoLabel" for="revisado">
                    <Translate contentKey="myappApp.tituloSecundario.revisado">Revisado</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-revisado"
                    type="text"
                    name="revisado"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="ingresoLabel" for="ingreso">
                    <Translate contentKey="myappApp.tituloSecundario.ingreso">Ingreso</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-ingreso"
                    type="date"
                    className="form-control"
                    name="ingreso"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="egresoLabel" for="egreso">
                    <Translate contentKey="myappApp.tituloSecundario.egreso">Egreso</Translate>
                  </Label>
                  <AvField
                    id="titulo-secundario-my-suffix-egreso"
                    type="date"
                    className="form-control"
                    name="egreso"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="nroCue.id">
                    <Translate contentKey="myappApp.tituloSecundario.nroCue">Nro Cue</Translate>
                  </Label>
                  <AvInput id="titulo-secundario-my-suffix-nroCue" type="select" className="form-control" name="nroCue.id">
                    <option value="" key="0" />
                    {establecimientos
                      ? establecimientos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/titulo-secundario-my-suffix" replace color="info">
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
  establecimientos: storeState.establecimiento.entities,
  tituloSecundarioEntity: storeState.tituloSecundario.entity,
  loading: storeState.tituloSecundario.loading,
  updating: storeState.tituloSecundario.updating,
  updateSuccess: storeState.tituloSecundario.updateSuccess
});

const mapDispatchToProps = {
  getEstablecimientos,
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
)(TituloSecundarioMySuffixUpdate);
