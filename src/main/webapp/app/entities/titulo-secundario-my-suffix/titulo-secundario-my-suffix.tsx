import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './titulo-secundario-my-suffix.reducer';
import { ITituloSecundarioMySuffix } from 'app/shared/model/titulo-secundario-my-suffix.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITituloSecundarioMySuffixProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export interface ITituloSecundarioMySuffixState {
  search: string;
}

export class TituloSecundarioMySuffix extends React.Component<ITituloSecundarioMySuffixProps, ITituloSecundarioMySuffixState> {
  state: ITituloSecundarioMySuffixState = {
    search: ''
  };

  componentDidMount() {
    this.props.getEntities();
  }

  search = () => {
    if (this.state.search) {
      this.props.getSearchEntities(this.state.search);
    }
  };

  clear = () => {
    this.setState({ search: '' }, () => {
      this.props.getEntities();
    });
  };

  handleSearch = event => this.setState({ search: event.target.value });

  render() {
    const { tituloSecundarioList, match } = this.props;
    return (
      <div>
        <h2 id="titulo-secundario-my-suffix-heading">
          <Translate contentKey="myappApp.tituloSecundario.home.title">Titulo Secundarios</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myappApp.tituloSecundario.home.createLabel">Create new Titulo Secundario</Translate>
          </Link>
        </h2>
        <Row>
          <Col sm="12">
            <AvForm onSubmit={this.search}>
              <AvGroup>
                <InputGroup>
                  <AvInput
                    type="text"
                    name="search"
                    value={this.state.search}
                    onChange={this.handleSearch}
                    placeholder={translate('myappApp.tituloSecundario.home.search')}
                  />
                  <Button className="input-group-addon">
                    <FontAwesomeIcon icon="search" />
                  </Button>
                  <Button type="reset" className="input-group-addon" onClick={this.clear}>
                    <FontAwesomeIcon icon="trash" />
                  </Button>
                </InputGroup>
              </AvGroup>
            </AvForm>
          </Col>
        </Row>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.nroTitulo">Nro Titulo</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.tipoEjemplar">Tipo Ejemplar</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.nombre">Nombre</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.apellido">Apellido</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.dni">Dni</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.fechaNacimiento">Fecha Nacimiento</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.tituloOtorgado">Titulo Otorgado</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.promedio">Promedio</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.mesAnnioEgreso">Mes Annio Egreso</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.validezNacional">Validez Nacional</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.dictamen">Dictamen</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.revisado">Revisado</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.ingreso">Ingreso</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.egreso">Egreso</Translate>
                </th>
                <th>
                  <Translate contentKey="myappApp.tituloSecundario.nroCue">Nro Cue</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tituloSecundarioList.map((tituloSecundario, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${tituloSecundario.id}`} color="link" size="sm">
                      {tituloSecundario.id}
                    </Button>
                  </td>
                  <td>{tituloSecundario.nroTitulo}</td>
                  <td>
                    <Translate contentKey={`myappApp.TipoEjemplar.${tituloSecundario.tipoEjemplar}`} />
                  </td>
                  <td>{tituloSecundario.nombre}</td>
                  <td>{tituloSecundario.apellido}</td>
                  <td>
                    <Translate contentKey={`myappApp.TipoDni.${tituloSecundario.dni}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={tituloSecundario.fechaNacimiento} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{tituloSecundario.tituloOtorgado}</td>
                  <td>{tituloSecundario.promedio}</td>
                  <td>{tituloSecundario.mesAnnioEgreso}</td>
                  <td>{tituloSecundario.validezNacional}</td>
                  <td>{tituloSecundario.dictamen}</td>
                  <td>{tituloSecundario.revisado}</td>
                  <td>
                    <TextFormat type="date" value={tituloSecundario.ingreso} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={tituloSecundario.egreso} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {tituloSecundario.nroCue ? (
                      <Link to={`establecimiento-my-suffix/${tituloSecundario.nroCue.id}`}>{tituloSecundario.nroCue.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tituloSecundario.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tituloSecundario.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tituloSecundario.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ tituloSecundario }: IRootState) => ({
  tituloSecundarioList: tituloSecundario.entities
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(TituloSecundarioMySuffix);
