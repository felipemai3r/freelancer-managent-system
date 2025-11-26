import { useCallback } from "react";
import ProjetoService from "../api/projetoService";
import AtividadeService from "../api/atividadeService";
import ProjetoFreelancerService from "../api/projetoFreelancerService";

export default function useProject(projetoId) {
  const fetchProjeto = useCallback(async () => {
    return ProjetoService.getById(projetoId);
  }, [projetoId]);

  const fetchAtividades = useCallback(async () => {
    return AtividadeService.listByProject(projetoId);
  }, [projetoId]);

  const fetchFreelancers = useCallback(async () => {
    return ProjetoFreelancerService.listByProject(projetoId);
  }, [projetoId]);

  return { fetchProjeto, fetchAtividades, fetchFreelancers };
}
