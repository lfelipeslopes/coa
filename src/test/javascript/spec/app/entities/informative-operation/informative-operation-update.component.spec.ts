/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CoaTestModule } from '../../../test.module';
import { InformativeOperationUpdateComponent } from 'app/entities/informative-operation/informative-operation-update.component';
import { InformativeOperationService } from 'app/entities/informative-operation/informative-operation.service';
import { InformativeOperation } from 'app/shared/model/informative-operation.model';

describe('Component Tests', () => {
    describe('InformativeOperation Management Update Component', () => {
        let comp: InformativeOperationUpdateComponent;
        let fixture: ComponentFixture<InformativeOperationUpdateComponent>;
        let service: InformativeOperationService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CoaTestModule],
                declarations: [InformativeOperationUpdateComponent]
            })
                .overrideTemplate(InformativeOperationUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(InformativeOperationUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InformativeOperationService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new InformativeOperation(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.informativeOperation = entity;
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
                    const entity = new InformativeOperation();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.informativeOperation = entity;
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
