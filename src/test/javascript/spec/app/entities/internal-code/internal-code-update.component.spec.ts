/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { InternalCodeUpdateComponent } from 'app/entities/internal-code/internal-code-update.component';
import { InternalCodeService } from 'app/entities/internal-code/internal-code.service';
import { InternalCode } from 'app/shared/model/internal-code.model';

describe('Component Tests', () => {
    describe('InternalCode Management Update Component', () => {
        let comp: InternalCodeUpdateComponent;
        let fixture: ComponentFixture<InternalCodeUpdateComponent>;
        let service: InternalCodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [InternalCodeUpdateComponent]
            })
                .overrideTemplate(InternalCodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InternalCodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InternalCodeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InternalCode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.internalCode = entity;
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
                    const entity = new InternalCode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.internalCode = entity;
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
