import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IBook } from 'app/shared/model/book.model';
import { getEntities as getBooks } from 'app/entities/book/book.reducer';
import { getEntity, updateEntity, createEntity, reset } from './editorial.reducer';
import { IEditorial } from 'app/shared/model/editorial.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IEditorialUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IEditorialUpdateState {
  isNew: boolean;
  bookId: string;
}

export class EditorialUpdate extends React.Component<IEditorialUpdateProps, IEditorialUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      bookId: '0',
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

    this.props.getBooks();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { editorialEntity } = this.props;
      const entity = {
        ...editorialEntity,
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
    this.props.history.push('/entity/editorial');
  };

  render() {
    const { editorialEntity, books, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="myappApp.editorial.home.createOrEditLabel">
              <Translate contentKey="myappApp.editorial.home.createOrEditLabel">Create or edit a Editorial</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : editorialEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="editorial-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nombreLabel" for="nombre">
                    <Translate contentKey="myappApp.editorial.nombre">Nombre</Translate>
                  </Label>
                  <AvField id="editorial-nombre" type="text" name="nombre" />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaLabel" for="fecha">
                    <Translate contentKey="myappApp.editorial.fecha">Fecha</Translate>
                  </Label>
                  <AvField id="editorial-fecha" type="date" className="form-control" name="fecha" />
                </AvGroup>
                <AvGroup>
                  <Label for="book.title">
                    <Translate contentKey="myappApp.editorial.book">Book</Translate>
                  </Label>
                  <AvInput id="editorial-book" type="select" className="form-control" name="book.id">
                    <option value="" key="0" />
                    {books
                      ? books.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.title}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/editorial" replace color="info">
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
  books: storeState.book.entities,
  editorialEntity: storeState.editorial.entity,
  loading: storeState.editorial.loading,
  updating: storeState.editorial.updating,
  updateSuccess: storeState.editorial.updateSuccess
});

const mapDispatchToProps = {
  getBooks,
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
)(EditorialUpdate);
