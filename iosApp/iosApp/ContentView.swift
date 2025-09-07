import UIKit
import SwiftUI
import ComposeApp
import StoreKit

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        let controller:UIViewController = MainViewControllerKt.MainViewController(nativeViewFactory: IosNativeViewFactory.shared) as UIViewController
       // controller.present(controller, animated: true, completion: nil)
        // SKStoreReviewController.requestReview()

       /*  DispatchQueue.main.async {
            let sheetController = UIViewController()
          //  sheetController.view.backgroundColor = .systemBackground
            controller.present(sheetController, animated: true, completion: nil)
        }
 */
        return controller
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        /* DispatchQueue.main.async {
            let sheetController = UIViewController()
             sheetController.view.backgroundColor = .systemBackground
            uiViewController.present(sheetController, animated: true, completion: nil)
        } */
        print("estou atualizando")
    }

}

struct ContentView: View {
    var body: some View {
        ComposeView()
            .ignoresSafeArea(edges: .all)
            .ignoresSafeArea(.keyboard)
    }
}
