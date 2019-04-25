/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { InternalCodeDeleteDialogComponent } from 'app/entities/internal-code/internal-code-delete-dialog.component';
import { InternalCodeService } from 'app/entities/internal-code/internal-code.service';

describe('Component Tests', () => {
    describe('InternalCode Management Delete Component', () => {
        let comp: InternalCodeDeleteDialogComponent;
        let fixture: ComponentFixture<InternalCodeDeleteDialogComponent>;
        let service: InternalCodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [InternalCodeDeleteDialogComponent]
            })
                .overrideTemplate(InternalCodeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InternalCodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternalCodeService);
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
