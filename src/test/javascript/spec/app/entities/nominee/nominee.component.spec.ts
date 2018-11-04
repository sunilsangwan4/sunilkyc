/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { NomineeComponent } from 'app/entities/nominee/nominee.component';
import { NomineeService } from 'app/entities/nominee/nominee.service';
import { Nominee } from 'app/shared/model/nominee.model';

describe('Component Tests', () => {
    describe('Nominee Management Component', () => {
        let comp: NomineeComponent;
        let fixture: ComponentFixture<NomineeComponent>;
        let service: NomineeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [NomineeComponent],
                providers: []
            })
                .overrideTemplate(NomineeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NomineeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NomineeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Nominee(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.nominees[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
