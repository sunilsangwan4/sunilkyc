/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TypeCdDetailComponent } from 'app/entities/type-cd/type-cd-detail.component';
import { TypeCd } from 'app/shared/model/type-cd.model';

describe('Component Tests', () => {
    describe('TypeCd Management Detail Component', () => {
        let comp: TypeCdDetailComponent;
        let fixture: ComponentFixture<TypeCdDetailComponent>;
        const route = ({ data: of({ typeCd: new TypeCd(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TypeCdDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeCdDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeCdDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeCd).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
