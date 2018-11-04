/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { DepositoryInfoUpdateComponent } from 'app/entities/depository-info/depository-info-update.component';
import { DepositoryInfoService } from 'app/entities/depository-info/depository-info.service';
import { DepositoryInfo } from 'app/shared/model/depository-info.model';

describe('Component Tests', () => {
    describe('DepositoryInfo Management Update Component', () => {
        let comp: DepositoryInfoUpdateComponent;
        let fixture: ComponentFixture<DepositoryInfoUpdateComponent>;
        let service: DepositoryInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [DepositoryInfoUpdateComponent]
            })
                .overrideTemplate(DepositoryInfoUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepositoryInfoUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositoryInfoService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DepositoryInfo(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depositoryInfo = entity;
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
                    const entity = new DepositoryInfo();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depositoryInfo = entity;
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
