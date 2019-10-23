import { Output, Component, EventEmitter, ElementRef, Input } from '@angular/core';

@Component({
  selector: 'color-node',
  styles: ['.color-nodes-list { padding: 10px; }', '.color-node { padding: 5px; width: 20px; height: 20px; border-radius: 50%; background-color: #FFF; float: left; margin-right: 10px; cursor:pointer; }'],
  template: `
    <div [style.background]="color"
    [cpPosition]="'top'"
    [cpPositionOffset]="'50%'"
    [cpPositionRelativeToArrow]="true"
    [cpOutputFormat]="rgba"
    (colorPickerChange)="colorPickerChange(color)"
    [(colorPicker)]="color" class="color-node"></div>`
})
export class ColorNodeComponent {
    @Input('color') color: string = "#FFFFFF"
    @Output() onSelectValue = new EventEmitter<any>()

    constructor(private elementRef: ElementRef) { }

    public get target(): HTMLElement {
        return this.elementRef.nativeElement as HTMLElement;
    }

    colorPickerChange( value: string ) {
        this.color = value
        if (this.color != undefined)
            this.onSelectValue.emit(this)
    }
}