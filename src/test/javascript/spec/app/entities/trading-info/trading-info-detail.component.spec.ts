/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TradingInfoDetailComponent } from 'app/entities/trading-info/trading-info-detail.component';
import { TradingInfo } from 'app/shared/model/trading-info.model';

describe('Component Tests', () => {
    describe('TradingInfo Management Detail Component', () => {
        let comp: TradingInfoDetailComponent;
        let fixture: ComponentFixture<TradingInfoDetailComponent>;
        const route = ({ data: of({ tradingInfo: new TradingInfo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TradingInfoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TradingInfoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TradingInfoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tradingInfo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
