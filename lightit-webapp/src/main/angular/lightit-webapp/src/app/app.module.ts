import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PressurePanelComponent } from './components/pressure-panel/panel.component'
import { LedPanelComponent } from './components/led-panel/panel.component'
import { ModePanelComponent } from './components/mode-panel/panel.component'
import { GraphComponent } from './components/graph/graph.component'
import { OverlayPanelComponent } from './components/overlay-panel/panel.component'
import { ColorPickerModule } from 'ngx-color-picker';
import { ColorNodeComponent } from './components/mode-panel/color-node.component'

@NgModule({
  declarations: [
    AppComponent,
    PressurePanelComponent,
    ModePanelComponent,
    GraphComponent,
    ColorNodeComponent,
    OverlayPanelComponent,
    LedPanelComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ColorPickerModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
