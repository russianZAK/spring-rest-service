package by.russianzak.service;

import java.util.List;
import java.util.Optional;

public interface Service<REQUESTDTO, RESPONSEDTO, K> {

  RESPONSEDTO save(REQUESTDTO request);

  RESPONSEDTO getById(K id);

  void deleteById(K id);

  void delete(REQUESTDTO request);

  RESPONSEDTO update(REQUESTDTO request, K id);

  Optional<RESPONSEDTO> findById(K id);

  List<RESPONSEDTO> findAll();
}
