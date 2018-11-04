/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { Kyc5TestModule } from '../../../test.module';
import { GroupCdUpdateComponent } from 'app/entities/group-cd/group-cd-update.component';
import { GroupCdService } from 'app/entities/group-cd/group-cd.service';
import { GroupCd } from 'app/shared/model/group-cd.model';

describe('Component Tests', () => {
    describe('GroupCd Management Update Component', () => {
        let comp: GroupCdUpdateComponent;
        let fixture: ComponentFixture<GroupCdUpdateComponent>;
        let service: GroupCdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [Kyc5TestModule],
                declarations: [GroupCdUpdateComponent]
            })
                .overrideTemplate(GroupCdUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GroupCdUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GroupCdService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GroupCd(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupCd = entity;
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
                    const entity = new GroupCd();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.groupCd = entity;
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
