/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { TradingInfoComponent } from 'app/entities/trading-info/trading-info.component';
import { TradingInfoService } from 'app/entities/trading-info/trading-info.service';
import { TradingInfo } from 'app/shared/model/trading-info.model';

describe('Component Tests', () => {
    describe('TradingInfo Management Component', () => {
        let comp: TradingInfoComponent;
        let fixture: ComponentFixture<TradingInfoComponent>;
        let service: TradingInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TradingInfoComponent],
                providers: []
            })
                .overrideTemplate(TradingInfoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TradingInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TradingInfoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TradingInfo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tradingInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
