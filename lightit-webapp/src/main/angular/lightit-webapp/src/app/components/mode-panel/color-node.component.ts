import { Output, Component, EventEmitter } from '@angular/core';

@Component({
  selector: 'color-node',
  styles: ['.color-nodes-list { padding: 10px; }', '.color-node { padding: 5px; width: 20px; height: 20px; border-radius: 50%; background-color: #FFF; float: left; margin-right: 10px; }'],
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
    color: string
    @Output() onSelectValue = new EventEmitter<{ color: string }>()

    colorPickerChange( value: string ) {
        this.color = value
        if (this.color != undefined)
            this.onSelectValue.emit( { color: this.color } )
    }
}