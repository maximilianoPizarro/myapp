import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './titulo-secundario-my-suffix.reducer';
import { ITituloSecundarioMySuffix } from 'app/shared/model/titulo-secundario-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITituloSecundarioMySuffixDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class TituloSecundarioMySuffixDetail extends React.Component<ITituloSecundarioMySuffixDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { tituloSecundarioEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="myappApp.tituloSecundario.detail.title">TituloSecundario</Translate> [<b>{tituloSecundarioEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nroTitulo">
                <Translate contentKey="myappApp.tituloSecundario.nroTitulo">Nro Titulo</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.nroTitulo}</dd>
            <dt>
              <span id="tipoEjemplar">
                <Translate contentKey="myappApp.tituloSecundario.tipoEjemplar">Tipo Ejemplar</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.tipoEjemplar}</dd>
            <dt>
              <span id="nombre">
                <Translate contentKey="myappApp.tituloSecundario.nombre">Nombre</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.nombre}</dd>
            <dt>
              <span id="apellido">
                <Translate contentKey="myappApp.tituloSecundario.apellido">Apellido</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.apellido}</dd>
            <dt>
              <span id="dni">
                <Translate contentKey="myappApp.tituloSecundario.dni">Dni</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.dni}</dd>
            <dt>
              <span id="fechaNacimiento">
                <Translate contentKey="myappApp.tituloSecundario.fechaNacimiento">Fecha Nacimiento</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={tituloSecundarioEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="tituloOtorgado">
                <Translate contentKey="myappApp.tituloSecundario.tituloOtorgado">Titulo Otorgado</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.tituloOtorgado}</dd>
            <dt>
              <span id="promedio">
                <Translate contentKey="myappApp.tituloSecundario.promedio">Promedio</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.promedio}</dd>
            <dt>
              <span id="mesAnnioEgreso">
                <Translate contentKey="myappApp.tituloSecundario.mesAnnioEgreso">Mes Annio Egreso</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.mesAnnioEgreso}</dd>
            <dt>
              <span id="validezNacional">
                <Translate contentKey="myappApp.tituloSecundario.validezNacional">Validez Nacional</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.validezNacional}</dd>
            <dt>
              <span id="dictamen">
                <Translate contentKey="myappApp.tituloSecundario.dictamen">Dictamen</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.dictamen}</dd>
            <dt>
              <span id="revisado">
                <Translate contentKey="myappApp.tituloSecundario.revisado">Revisado</Translate>
              </span>
            </dt>
            <dd>{tituloSecundarioEntity.revisado}</dd>
            <dt>
              <span id="ingreso">
                <Translate contentKey="myappApp.tituloSecundario.ingreso">Ingreso</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={tituloSecundarioEntity.ingreso} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="egreso">
                <Translate contentKey="myappApp.tituloSecundario.egreso">Egreso</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={tituloSecundarioEntity.egreso} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="myappApp.tituloSecundario.nroCue">Nro Cue</Translate>
            </dt>
            <dd>{tituloSecundarioEntity.nroCue ? tituloSecundarioEntity.nroCue.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/titulo-secundario-my-suffix" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/titulo-secundario-my-suffix/${tituloSecundarioEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ tituloSecundario }: IRootState) => ({
  tituloSecundarioEntity: tituloSecundario.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TituloSecundarioMySuffixDetail);
