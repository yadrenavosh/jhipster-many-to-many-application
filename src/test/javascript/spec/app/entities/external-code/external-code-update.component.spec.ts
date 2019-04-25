/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { ExternalCodeUpdateComponent } from 'app/entities/external-code/external-code-update.component';
import { ExternalCodeService } from 'app/entities/external-code/external-code.service';
import { ExternalCode } from 'app/shared/model/external-code.model';

describe('Component Tests', () => {
    describe('ExternalCode Management Update Component', () => {
        let comp: ExternalCodeUpdateComponent;
        let fixture: ComponentFixture<ExternalCodeUpdateComponent>;
        let service: ExternalCodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [ExternalCodeUpdateComponent]
            })
                .overrideTemplate(ExternalCodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ExternalCodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ExternalCodeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExternalCode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.externalCode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ExternalCode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.externalCode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
