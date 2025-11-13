package inu.unithon.backend.domain.translate.client;

public interface PapagoClient {
  public String translate(String text, String source, String target);
}
