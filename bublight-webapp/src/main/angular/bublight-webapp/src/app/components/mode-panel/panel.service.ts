import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class LedPanelService {
  constructor(private http: HttpClient) { }

  getRainbowMode() {
      return this.http.get("http://localhost:8080/mode/base/simpleRainbowMode")
  }
}