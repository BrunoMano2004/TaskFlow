package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    public ListagemEtiquetaDto buscarEtiqueta(String nomeEtiqueta, String emailUsuario) {
        Etiqueta etiqueta = etiquetaRepository.retornarEtiquetaComNomeEEmailUsuario(nomeEtiqueta, emailUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Etiqueta ou email não encontrados!"));

        return new ListagemEtiquetaDto(etiqueta);
    }
}
