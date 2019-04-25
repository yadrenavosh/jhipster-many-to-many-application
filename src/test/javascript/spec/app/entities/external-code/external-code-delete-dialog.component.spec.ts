/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { ExternalCodeDeleteDialogComponent } from 'app/entities/external-code/external-code-delete-dialog.component';
import { ExternalCodeService } from 'app/entities/external-code/external-code.service';

describe('Component Tests', () => {
    describe('ExternalCode Management Delete Component', () => {
        let comp: ExternalCodeDeleteDialogComponent;
        let fixture: ComponentFixture<ExternalCodeDeleteDialogComponent>;
        let service: ExternalCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [ExternalCodeDeleteDialogComponent]
            })
                .overrideTemplate(ExternalCodeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExternalCodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalCodeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
