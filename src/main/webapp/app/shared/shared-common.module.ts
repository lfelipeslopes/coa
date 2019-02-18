import { NgModule } from '@angular/core';

import { CoaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [CoaSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [CoaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class CoaSharedCommonModule {}
