/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { DepositoryInfoDetailComponent } from 'app/entities/depository-info/depository-info-detail.component';
import { DepositoryInfo } from 'app/shared/model/depository-info.model';

describe('Component Tests', () => {
    describe('DepositoryInfo Management Detail Component', () => {
        let comp: DepositoryInfoDetailComponent;
        let fixture: ComponentFixture<DepositoryInfoDetailComponent>;
        const route = ({ data: of({ depositoryInfo: new DepositoryInfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [DepositoryInfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DepositoryInfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepositoryInfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.depositoryInfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
