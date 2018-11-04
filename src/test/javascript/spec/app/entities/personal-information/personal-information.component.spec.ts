/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Kyc5TestModule } from '../../../test.module';
import { PersonalInformationComponent } from 'app/entities/personal-information/personal-information.component';
import { PersonalInformationService } from 'app/entities/personal-information/personal-information.service';
import { PersonalInformation } from 'app/shared/model/personal-information.model';

describe('Component Tests', () => {
    describe('PersonalInformation Management Component', () => {
        let comp: PersonalInformationComponent;
        let fixture: ComponentFixture<PersonalInformationComponent>;
        let service: PersonalInformationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [PersonalInformationComponent],
                providers: []
            })
                .overrideTemplate(PersonalInformationComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PersonalInformationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonalInformationService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new PersonalInformation(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.personalInformations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
