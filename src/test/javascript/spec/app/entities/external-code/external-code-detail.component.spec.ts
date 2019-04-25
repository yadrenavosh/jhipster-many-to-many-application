/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { ExternalCodeDetailComponent } from 'app/entities/external-code/external-code-detail.component';
import { ExternalCode } from 'app/shared/model/external-code.model';

describe('Component Tests', () => {
    describe('ExternalCode Management Detail Component', () => {
        let comp: ExternalCodeDetailComponent;
        let fixture: ComponentFixture<ExternalCodeDetailComponent>;
        const route = ({ data: of({ externalCode: new ExternalCode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [ExternalCodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ExternalCodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ExternalCodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.externalCode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
