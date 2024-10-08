package TaskFlow_api.TaskFlow_api.service;

import TaskFlow_api.TaskFlow_api.dto.etiqueta.CadastroEtiquetaDto;
import TaskFlow_api.TaskFlow_api.dto.etiqueta.ListagemEtiquetaDto;
import TaskFlow_api.TaskFlow_api.exception.ResourceNotFoundException;
import TaskFlow_api.TaskFlow_api.model.Etiqueta;
import TaskFlow_api.TaskFlow_api.model.Usuario;
import TaskFlow_api.TaskFlow_api.repository.EtiquetaRepository;
import TaskFlow_api.TaskFlow_api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ListagemEtiquetaDto> buscarEtiqueta(String nomeEtiqueta) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        List<Etiqueta> etiquetas = etiquetaRepository.retornarEtiquetaComNomeEUsuario(nomeEtiqueta, usuario);

        List<ListagemEtiquetaDto> listagemEtiquetas = etiquetas.stream()
                .map(ListagemEtiquetaDto::new)
                .toList();

        return listagemEtiquetas;
    }

    public List<ListagemEtiquetaDto> retornarTodasEtiquetasPorUsuario() {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        List<Etiqueta> etiquetas = etiquetaRepository.retornarEtiquetaPorUsuario(usuario.getId());

        List<ListagemEtiquetaDto> listagemEtiqueta = etiquetas.stream()
                .map(ListagemEtiquetaDto::new)
                .toList();

        return listagemEtiqueta;
    }

    public void cadastrarEtiqueta(@Valid CadastroEtiquetaDto cadastroEtiqueta) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        etiquetaRepository.save(new Etiqueta(cadastroEtiqueta, usuario));
    }

    public void excluirEtiqueta(Long idEtiqueta) {
        Etiqueta etiqueta = etiquetaRepository
                .findById(idEtiqueta)
                .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada!"));

        etiquetaRepository.delete(etiqueta);
    }

    public ListagemEtiquetaDto retornarEtiquetaPeloId(Long idEtiqueta) {
        Usuario usuario = SecurityContextService.retornarLogin().getUsuario();

        Etiqueta etiqueta = etiquetaRepository
                .retornarEtiquetaDeUmUsuarioPeloId(idEtiqueta, usuario)
                .orElseThrow(() -> new ResourceNotFoundException("Etiqueta não encontrada!"));

        return new ListagemEtiquetaDto(etiqueta);
    }
}
