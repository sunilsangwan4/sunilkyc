/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { DepositoryInfoComponent } from 'app/entities/depository-info/depository-info.component';
import { DepositoryInfoService } from 'app/entities/depository-info/depository-info.service';
import { DepositoryInfo } from 'app/shared/model/depository-info.model';

describe('Component Tests', () => {
    describe('DepositoryInfo Management Component', () => {
        let comp: DepositoryInfoComponent;
        let fixture: ComponentFixture<DepositoryInfoComponent>;
        let service: DepositoryInfoService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [DepositoryInfoComponent],
                providers: []
            })
                .overrideTemplate(DepositoryInfoComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepositoryInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepositoryInfoService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DepositoryInfo(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.depositoryInfos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
