/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { TradingInfoUpdateComponent } from 'app/entities/trading-info/trading-info-update.component';
import { TradingInfoService } from 'app/entities/trading-info/trading-info.service';
import { TradingInfo } from 'app/shared/model/trading-info.model';

describe('Component Tests', () => {
    describe('TradingInfo Management Update Component', () => {
        let comp: TradingInfoUpdateComponent;
        let fixture: ComponentFixture<TradingInfoUpdateComponent>;
        let service: TradingInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [TradingInfoUpdateComponent]
            })
                .overrideTemplate(TradingInfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TradingInfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TradingInfoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TradingInfo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tradingInfo = entity;
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
                    const entity = new TradingInfo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tradingInfo = entity;
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
