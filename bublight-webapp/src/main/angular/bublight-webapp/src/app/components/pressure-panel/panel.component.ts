import { Component, Input } from '@angular/core';

@Component({
    selector: 'pressure-panel',
    templateUrl: './panel.component.html'
})
export class PressurePanelComponent {
    @Input('title') title: string;
    @Input('value') value: number;
    @Input('icon') icon: string;
}
