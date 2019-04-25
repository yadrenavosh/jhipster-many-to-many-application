/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterMtomApplicationTestModule } from '../../../test.module';
import { InternalCodeDetailComponent } from 'app/entities/internal-code/internal-code-detail.component';
import { InternalCode } from 'app/shared/model/internal-code.model';

describe('Component Tests', () => {
    describe('InternalCode Management Detail Component', () => {
        let comp: InternalCodeDetailComponent;
        let fixture: ComponentFixture<InternalCodeDetailComponent>;
        const route = ({ data: of({ internalCode: new InternalCode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterMtomApplicationTestModule],
                declarations: [InternalCodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(InternalCodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InternalCodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.internalCode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
