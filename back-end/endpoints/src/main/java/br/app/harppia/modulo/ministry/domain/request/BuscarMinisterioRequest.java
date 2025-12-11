package br.app.harppia.modulo.ministry.domain.request;

import java.util.UUID;

public record BuscarMinisterioRequest(UUID idIgreja, String nome) {

}
