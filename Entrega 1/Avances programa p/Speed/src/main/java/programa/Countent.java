/*
 * The MIT License
 *
 * Copyright 2018 ws.duarte.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package programa;

/**
 *
 * @author ws.duarte
 */
public class Countent {
    public String id;
	public String timestamp;
	public int alertaId;
	public String mensajeAlerta;
	public int idDispositivo;
	public int torre;
	public int apto;

	public Countent(String id, String timestamp, int alertaId, String mensajeAlerta, int idDispositivo, int torre, int apto) {
		this.alertaId = alertaId;
		this.mensajeAlerta = mensajeAlerta;
		this.idDispositivo = idDispositivo;
		this.torre = torre;
		this.apto = apto;
		this.id = id;
		this.timestamp = timestamp;
	}

	public Countent() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getAlertaId() {
		return alertaId;
	}

	public void setAlertaId(int alertaId) {
		this.alertaId = alertaId;
	}

	public String getMensajeAlerta() {
		return mensajeAlerta;
	}

	public void setMensajeAlerta(String mensajeAlerta) {
		this.mensajeAlerta = mensajeAlerta;
	}

	public int getIdDispositivo() {
		return idDispositivo;
	}

	public void setIdDispositivo(int idDispositivo) {
		this.idDispositivo = idDispositivo;
	}

	public int getTorre() {
		return torre;
	}

	public void setTorre(int torre) {
		this.torre = torre;
	}

	public int getApto() {
		return apto;
	}

	public void setApto(int apto) {
		this.apto = apto;
	}

	@Override
	public String toString() {
		return timestamp + ": [ " + mensajeAlerta + " ]";
	}

	public String toText() {
		return "[timestamp=" + timestamp + ", info=" + "[alertaId=" + alertaId + ", mensajeAlerta=" + mensajeAlerta
				+ ", idDispositivo=" + idDispositivo + ", torre=" + torre + ", apto=" + apto + "]" + "]";
	}
}
